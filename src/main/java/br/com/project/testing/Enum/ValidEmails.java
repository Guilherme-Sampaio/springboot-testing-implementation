package br.com.project.testing.Enum;

public enum ValidEmails {
  HOTMAIL("@hotmail.com"),
  GMAIL("@gmail.com"),
  OUTLOOK("@outlook.com");

  public final String description;

  ValidEmails(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }
}
