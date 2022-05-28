package com.bbubbush.board.validator;

import com.bbubbush.board.annotation.DuplicateListValue;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.List;

@Component
public class DuplicateListValueValidator implements ConstraintValidator<DuplicateListValue, List<String>> {

  @Override
  public boolean isValid(List<String> value, ConstraintValidatorContext context) {
    final HashSet<String> notDuplicateTags = new HashSet<>(value);
    return notDuplicateTags.size() == value.size();
  }

}
