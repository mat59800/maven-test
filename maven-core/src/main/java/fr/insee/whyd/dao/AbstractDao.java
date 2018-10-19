package fr.insee.whyd.dao;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import fr.insee.whyd.WhydConfig;

public abstract class AbstractDao<T> {

  protected ObjectMapper mapper = new ObjectMapper();

  abstract String getRelativeFolderPath();

  abstract ObjectReader getReader();

  abstract ObjectWriter getWriter();

  public T getById(String id) {
    Path persistFolder =
        Paths.get(WhydConfig.getInstance().getConfig().getString("whyd.persistFolder"))
            .resolve(getRelativeFolderPath());
    persistFolder.toFile().mkdirs();

    try {
      return getReader().readValue(persistFolder.resolve(id).toFile());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  };

  public T createOrUpdate(String id, T modelObject) {
    Path persistFolder =
        Paths.get(WhydConfig.getInstance().getConfig().getString("whyd.persistFolder"))
            .resolve(getRelativeFolderPath());
    persistFolder.toFile().mkdirs();

    try {
      getWriter().writeValue(persistFolder.resolve(id).toFile(), modelObject);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return modelObject;

  };

  public void delete(String id) {
    Path persistFolder =
        Paths.get(WhydConfig.getInstance().getConfig().getString("whyd.persistFolder"))
            .resolve(getRelativeFolderPath());
    persistFolder.toFile().mkdirs();
    try {
      Files.deleteIfExists(persistFolder.resolve(id));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  };

  public List<T> getAll() {
    Path persistFolder =
        Paths.get(WhydConfig.getInstance().getConfig().getString("whyd.persistFolder"))
            .resolve(getRelativeFolderPath());
    persistFolder.toFile().mkdirs();

    List<T> results = new ArrayList<>();

    for (File f : persistFolder.toFile().listFiles()) {
      try {
        results.add(getReader().readValue(f));
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
    return results;
  };

}
