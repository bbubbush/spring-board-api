package com.bbubbush.board.service;

import com.bbubbush.board.annotation.ExcelColumn;
import com.bbubbush.board.exception.NotMatchExcelHeaderException;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ExcelService {

  private static final String SETTER_METHOD_PREFIX = "set";
  private static final int FIRST_SHEET_INDEX = 0;
  private static final int HEADER_ROW_INDEX = 0;

  public static <T> List<T> transformToDto(MultipartFile uploadFile, Class<T> clazz) {
    XSSFSheet firstSheet = getSheet(uploadFile, FIRST_SHEET_INDEX);
    List<T> result = new ArrayList<>();

    final List<Field> excelHeaders = getExcelHeaders(firstSheet.getRow(HEADER_ROW_INDEX), clazz);
    if (excelHeaders.size() == 0) {
      throw new NotMatchExcelHeaderException();
    }

    for (int i = 1; i < firstSheet.getPhysicalNumberOfRows(); i++) {
      final XSSFRow row = firstSheet.getRow(i);
      T targetClass = createTargetClass(clazz);
      setDataInFieldInClass(clazz, excelHeaders, row, targetClass);
      result.add(targetClass);
    }
    return result;
  }

  private static XSSFSheet getSheet(MultipartFile uploadFile, int sheetIndex) {
    XSSFWorkbook xssfWorkbook;
    XSSFSheet targetSheet = null;
    try {
      xssfWorkbook = XSSFWorkbookFactory.createWorkbook(OPCPackage.open(uploadFile.getInputStream()));
      targetSheet = xssfWorkbook.getSheetAt(sheetIndex);
    } catch (IllegalArgumentException | IOException | InvalidFormatException e) {
      e.printStackTrace();
    }
    return targetSheet;
  }

  private static <T> List<Field> getExcelHeaders(XSSFRow titleRow, Class<T> clazz) {
    List<Field> result = new ArrayList<>();
    for (int i = 0; i < titleRow.getPhysicalNumberOfCells(); i++) {
      final String columnTitle = titleRow.getCell(i).getStringCellValue();
      final Optional<Field> targetField = Arrays.stream(clazz.getDeclaredFields())
        .filter(ExcelService::hasExcelColumnAnnotationField)
        .filter(field -> ExcelService.matchFieldName(field, columnTitle))
        .findFirst();

      if (targetField.isEmpty()) {
        continue;
      }
      result.add(targetField.get());
    }
    return result;
  }

  private static boolean hasExcelColumnAnnotationField(Field field) {
    return Arrays.stream(field.getDeclaredAnnotations())
      .anyMatch(annotation -> annotation.annotationType().equals(ExcelColumn.class));
  }

  private static boolean matchFieldName(Field field, String columnTitle) {
    return field.getAnnotation(ExcelColumn.class).header()
      .equals(columnTitle);
  }

  private static <T> T createTargetClass(Class<T> clazz) {
    try {
      return clazz.getDeclaredConstructor().newInstance();
    } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
      throw new RuntimeException();
    }
  }

  private static <T> void setDataInFieldInClass(Class<T> clazz, List<Field> excelHeaders, XSSFRow row, T targetClass) {
    for (int i = 0; i < excelHeaders.size(); i++) {
      final XSSFCell cellData = row.getCell(i);
      if (cellData == null) {
        continue;
      }
      final Field excelHeader = excelHeaders.get(i);
      invokeSetterMethod(clazz, targetClass, cellData, excelHeader);
    }
  }

  private static <T> void invokeSetterMethod(Class<T> clazz, T targetClass, XSSFCell cellData, Field excelHeader) {
    final Class<?> targetFieldType = excelHeader.getType();
    try {
      final Method setterMethod = clazz.getDeclaredMethod(getSetterMethodName(excelHeader), targetFieldType);
      if (targetFieldType.equals(Long.class) || targetFieldType.equals(Integer.class)) {
        setterMethod.invoke(targetClass, cellData.getNumericCellValue());
      } else if (targetFieldType.equals(List.class)) {
        final List<String> tagList = Arrays.stream(cellData.getStringCellValue().split(","))
          .collect(Collectors.toList());
        setterMethod.invoke(targetClass, tagList);
      } else {
        setterMethod.invoke(targetClass, cellData.getStringCellValue());
      }
    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
      e.printStackTrace();
    }
  }

  private static String getSetterMethodName(Field field) {
    return SETTER_METHOD_PREFIX + StringUtils.capitalize(field.getName());
  }

}
