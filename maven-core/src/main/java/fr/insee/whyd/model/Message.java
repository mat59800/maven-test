package fr.insee.whyd.model;

import java.time.LocalDateTime;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

public class Message {

  private String id;
  private String content;

  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  private LocalDateTime creationDate;
  private boolean isPrivate;
  private User recipient;
  private User author;



  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public LocalDateTime getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(LocalDateTime creationDate) {
    this.creationDate = creationDate;
  }

  public boolean isPrivate() {
    return isPrivate;
  }

  public void setPrivate(boolean isPrivate) {
    this.isPrivate = isPrivate;
  }

  public User getRecipient() {
    return recipient;
  }

  public void setRecipient(User recipient) {
    this.recipient = recipient;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public User getAuthor() {
    return author;
  }

  public void setAuthor(User author) {
    this.author = author;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (author == null ? 0 : author.hashCode());
    result = prime * result + (content == null ? 0 : content.hashCode());
    result = prime * result + (creationDate == null ? 0 : creationDate.hashCode());
    result = prime * result + (id == null ? 0 : id.hashCode());
    result = prime * result + (isPrivate ? 1231 : 1237);
    result = prime * result + (recipient == null ? 0 : recipient.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Message other = (Message) obj;
    if (author == null) {
      if (other.author != null) {
        return false;
      }
    } else if (!author.equals(other.author)) {
      return false;
    }
    if (content == null) {
      if (other.content != null) {
        return false;
      }
    } else if (!content.equals(other.content)) {
      return false;
    }
    if (creationDate == null) {
      if (other.creationDate != null) {
        return false;
      }
    } else if (!creationDate.equals(other.creationDate)) {
      return false;
    }
    if (id == null) {
      if (other.id != null) {
        return false;
      }
    } else if (!id.equals(other.id)) {
      return false;
    }
    if (isPrivate != other.isPrivate) {
      return false;
    }
    if (recipient == null) {
      if (other.recipient != null) {
        return false;
      }
    } else if (!recipient.equals(other.recipient)) {
      return false;
    }
    return true;
  }


}
