package com.company.service;

import com.company.model.Asset;
import com.company.model.Dependency;

public interface DependencyManagement {
    boolean insertDependency(Dependency dependency);
    boolean mayDelete(Asset asset);
    void printDependencies();
}
