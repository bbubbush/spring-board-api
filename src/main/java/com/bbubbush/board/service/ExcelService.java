package com.bbubbush.board.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ExcelService {

  public static <T> List<T> transformToDto(MultipartFile uploadFile, Class<T> clazz) {
    XSSFSheet firstSheet = getSheet(uploadFile, 0);
    List<T> result = new ArrayList<>();
    for (int i = 1; i < firstSheet.getPhysicalNumberOfRows(); i++) {
      final XSSFRow row = firstSheet.getRow(i);
      final XSSFCell subject = row.getCell(0);
      final XSSFCell text = row.getCell(1);
      final XSSFCell writer = row.getCell(2);
      final XSSFCell tags = row.getCell(3);

      T targetClass = createTargetClass(clazz);
      try {
        final Method setSubjectMethod = clazz.getDeclaredMethod("setSubject", String.class);
        setSubjectMethod.invoke(targetClass, subject.getStringCellValue());
        final Method setTextMethod = clazz.getDeclaredMethod("setText", String.class);
        setTextMethod.invoke(targetClass, text.getStringCellValue());
        final Method setWriterMethod = clazz.getDeclaredMethod("setWriter", String.class);
        if (writer != null) {
          setWriterMethod.invoke(targetClass, writer.getStringCellValue());
        }
        final Method setTagsMethod = clazz.getDeclaredMethod("setTags", List.class);
        final List<String> tagList = Arrays.stream(tags.getStringCellValue().split(","))
          .collect(Collectors.toList());
        setTagsMethod.invoke(targetClass, tagList);

      } catch (NoSuchMethodException e) {
        e.printStackTrace();
      } catch (InvocationTargetException e) {
        e.printStackTrace();
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      }
      result.add(targetClass);
    }
    return result;
  }

  private static <T> List<Method> getSetterMethods(Class<T> clazz) {
    return Arrays.stream(clazz.getDeclaredMethods())
      .filter(method -> method.getName().startsWith("set"))
      .collect(Collectors.toList());
  }

  private static <T> List<Field> getFields(Class<T> clazz) {
    return Arrays.stream(clazz.getDeclaredFields())
      .collect(Collectors.toList());
  }

  private static <T> T createTargetClass(Class<T> clazz) {
    try {
      return clazz.getDeclaredConstructor().newInstance();
    } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
      throw new RuntimeException();
    }
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

}
