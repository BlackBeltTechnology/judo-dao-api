package hu.blackbelt.judo.dao.api;

public interface IdentifierProvider<ID> {
    ID get();
    Class<ID> getType();
    String getName();
}
