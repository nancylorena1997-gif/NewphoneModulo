package com.newphone.newphonemodulo.ui.config;

import com.newphone.newphonemodulo.dao.CrudDao;

import java.util.function.Function;
import java.util.function.Supplier;

public class ModuleDescriptor<T> {

    private final String key;
    private final String title;
    private final FieldConfig[] fields;
    private final String[] tableHeaders;
    private final CrudDao<T> dao;
    private final Supplier<T> entitySupplier;
    private final FormBinder<T> formBinder;
    private final Function<T, Object[]> rowMapper;
    private final Function<T, Object> idExtractor;

    public ModuleDescriptor(
            String key,
            String title,
            FieldConfig[] fields,
            String[] tableHeaders,
            CrudDao<T> dao,
            Supplier<T> entitySupplier,
            FormBinder<T> formBinder,
            Function<T, Object[]> rowMapper,
            Function<T, Object> idExtractor) {
        this.key = key;
        this.title = title;
        this.fields = fields;
        this.tableHeaders = tableHeaders;
        this.dao = dao;
        this.entitySupplier = entitySupplier;
        this.formBinder = formBinder;
        this.rowMapper = rowMapper;
        this.idExtractor = idExtractor;
    }

    public String getKey() {
        return key;
    }

    public String getTitle() {
        return title;
    }

    public FieldConfig[] getFields() {
        return fields;
    }

    public String[] getTableHeaders() {
        return tableHeaders;
    }

    public CrudDao<T> getDao() {
        return dao;
    }

    public Supplier<T> getEntitySupplier() {
        return entitySupplier;
    }

    public FormBinder<T> getFormBinder() {
        return formBinder;
    }

    public Function<T, Object[]> getRowMapper() {
        return rowMapper;
    }

    public Function<T, Object> getIdExtractor() {
        return idExtractor;
    }
}
