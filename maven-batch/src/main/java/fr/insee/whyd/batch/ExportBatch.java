package fr.insee.whyd.batch;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.slf4j.LoggerFactory;
import fr.insee.whyd.WhydConfig;
import fr.insee.whyd.services.WhydService;

public class ExportBatch {


  public static void main(String args[]) {
    File backup = new File(WhydConfig.getInstance().getConfig().getString("whyd.backup.folder")
        + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE) + ".zip");
    new File(WhydConfig.getInstance().getConfig().getString("whyd.backup.folder")).mkdirs();
    LoggerFactory.getLogger(ExportBatch.class).info("Exporting to " + backup.getAbsolutePath());

    try {
      WhydService.exportAll(new FileOutputStream(backup));
    } catch (FileNotFoundException e) {
      throw new RuntimeException("Impossible d'écrire le fichier de sauvegarde", e);

    }
  }
}
