package io.whitesource.cure;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FileUtilsTest {

  @Test
  void isFileInDir_outside_successfullyWithResult() throws IOException {
    Path sourcePath = Paths.get("src").toAbsolutePath();
    Path cwd = Paths.get("").toAbsolutePath();

    boolean isOutside = FileUtils.isFileOutsideDir(cwd.toString(), sourcePath.toString());
    Assertions.assertTrue(isOutside);
  }

  @Test
  void isFileInDir_inside_successfullyWithResult() throws IOException {
    Path sourcePath = Paths.get("src").toAbsolutePath();
    Path cwd = Paths.get("").toAbsolutePath();

    boolean isOutside = FileUtils.isFileOutsideDir(sourcePath.toString(), cwd.toString());
    Assertions.assertFalse(isOutside);
  }

  @Test
  void isFileInDir_null_successfully() {
    Assertions.assertThrows(
        NullPointerException.class, () -> FileUtils.isFileOutsideDir(null, null));

    Assertions.assertThrows(
        NullPointerException.class,
        () -> FileUtils.isFileOutsideDir("file-path-place-holder", null));

    Assertions.assertThrows(
        NullPointerException.class,
        () -> FileUtils.isFileOutsideDir(null, "base-dir-place-holder"));
  }

  @Test
  void normalize_validInput_successfullyWithResult() throws IOException {
    String validInput = "./In/../Valid/Un/../Normalized/./Path";
    String expectedResult = "Valid" + File.separator + "Normalized" + File.separator + "Path";

    String actualResult = FileUtils.normalize(validInput);
    Assertions.assertEquals(expectedResult, actualResult);
  }

  @Test
  void normalize_null_successfully() {
    Assertions.assertNull(FileUtils.normalize(null));
  }
}
