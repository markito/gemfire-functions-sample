package com.pivotal.gemfire.samples.entity;

import com.gemstone.gemfire.management.internal.cli.util.JsonUtil;

import java.io.Serializable;
import java.util.Objects;

/**
 * Created by markito on 4/08/14.
 */
public class Customer  implements Serializable {

  private Integer id;
  private String name, email;
  private String ccNumber;

  public Customer(Integer id, String name, String email, String ccNumber) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.ccNumber = ccNumber;
  }

  public Customer() {

  }
  /**
   * @return the id
   */
  public Integer getId() {
    return id;
  }

  /**
   * @param id the id to set
   */
  public void setId(Integer id) {
    this.id = id;
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 79 * hash + Objects.hashCode(this.id);
    hash = 79 * hash + Objects.hashCode(this.name);
    return hash;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final Customer other = (Customer) obj;
    if (!Objects.equals(this.id, other.id)) {
      return false;
    }
    if (!Objects.equals(this.name, other.name)) {
      return false;
    }
    return true;
  }

  public String toJson() {
    return JsonUtil.objectToJson(this);
  }

  @Override
  public String toString() {
    return String.format("Customer{id=%d, name=%s, email=%s, ccNumber=%s}", id, name, email, ccNumber);
  }

  /**
   * @param name the name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @return the email
   */
  public String getEmail() {
    return email;
  }

  /**
   * @param email the email to set
   */
  public void setEmail(String email) {
    this.email = email;
  }

  public String getCcNumber() {
    return ccNumber;
  }

  public void setCcNumber(String ccNumber) {
    this.ccNumber = ccNumber;
  }
}
