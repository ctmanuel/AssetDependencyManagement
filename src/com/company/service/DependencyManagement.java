package com.company.service;

import com.company.model.Asset;
import com.company.model.Dependency;

import java.util.concurrent.Future;

public interface DependencyManagement {
    Future<Boolean> insertDependency(Dependency dependency);
    Future<Boolean> mayDelete(Asset asset);
    void printDependencies();
}
