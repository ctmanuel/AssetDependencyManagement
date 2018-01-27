package com.company.service;

import com.company.model.Asset;
import com.company.model.Dependency;

import java.util.concurrent.Future;

/**
 * Interface for the DependencyManagement implementations
 * Includes functions for adding Dependencies and checking for valid
 * deletion of Assets
 */
public interface DependencyManagement {
    Future<Boolean> insertDependency(Dependency dependency);
    Future<Boolean> mayDelete(Asset asset);
    void printDependencies();
}
