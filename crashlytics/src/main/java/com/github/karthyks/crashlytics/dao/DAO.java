package com.github.karthyks.crashlytics.dao;


import android.content.ContentResolver;
import android.net.Uri;

import java.util.List;

abstract class DAO<T> {

  String authority;
  ContentResolver contentResolver;
  Uri uri;

  DAO(String authority, ContentResolver contentResolver, Uri uri) {
    this.authority = authority;
    this.contentResolver = contentResolver;
    this.uri = uri;
  }

  public abstract List<T> findAll();

  public abstract void deleteAll();

  public abstract void insertOne(T model);
}
