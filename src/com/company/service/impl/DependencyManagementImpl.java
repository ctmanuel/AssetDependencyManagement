package com.company.service.impl;

import com.company.model.Asset;
import com.company.model.Dependency;
import com.company.service.DependencyManagement;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class DependencyManagementImpl implements DependencyManagement{

    private ExecutorService executorService;

    private static DependencyManagementImpl instance;
    private List<Dependency> dependencies;

    private DependencyManagementImpl() {
        dependencies = new ArrayList<>();
        executorService = Executors.newSingleThreadExecutor();
    }

    public static synchronized DependencyManagementImpl getInstance() {
        if(instance == null) {
            instance = new DependencyManagementImpl();
        }
        return instance;
    }

    @Override
    public Future<Boolean> insertDependency(Dependency dependency) {
        return executorService.submit(() -> dependencyIsValid(dependency) && dependencies.add(dependency));
    }

    @Override
    public Future<Boolean> mayDelete(Asset asset) {
        return executorService.submit(() -> assetExists(asset) && !hasParent(asset));
    }

    private boolean dependencyIsValid(Dependency dependency) {
        String newFromAssetID = dependency.getFromAsset().getId();
        return !isCircularDependency(dependency, newFromAssetID) && !dependencyExists(dependency);
    }

    private boolean isCircularDependency(Dependency dependency, String assetToCheck) {
        String newToAssetID = dependency.getToAsset().getId();
        List<Dependency> dependencySubset = findDependencies(newToAssetID);
        for (Dependency dependency1 : dependencySubset) {
            if (isCircularDependency(dependency1, assetToCheck)) {
                return true;
            }
        }
        return dependency.getToAsset().getId().equals(assetToCheck);
    }

    private boolean dependencyExists(Dependency dependency){
        String newToAssetID = dependency.getToAsset().getId();
        String newFromAssetID = dependency.getFromAsset().getId();

        Predicate<Dependency> check = dependency1 -> dependency1.getFromAsset().getId().equals(newFromAssetID) && dependency1.getToAsset().getId().equals(newToAssetID);
        return dependencies.stream().anyMatch(check);
    }

    private List<Dependency> findDependencies(String assetID) {
        Predicate<Dependency> check = dependency -> dependency.getFromAsset().getId().equals(assetID);
        return dependencies.stream().filter(check).collect(Collectors.toList());
    }

    private boolean assetExists(Asset asset) {
        String assetId = asset.getId();

        Predicate<Dependency> check = dependency1 -> dependency1.getFromAsset().getId().equals(assetId) || dependency1.getToAsset().getId().equals(assetId);
        return dependencies.stream().anyMatch(check);
    }

    private boolean hasParent(Asset asset) {
        String assetId = asset.getId();

        Predicate<Dependency> check = dependency1 -> dependency1.getToAsset().getId().equals(assetId);
        return dependencies.stream().anyMatch(check);
    }

    public List<Dependency> getDependencies() {
        return dependencies;
    }

    @Override
    public void printDependencies(){
        dependencies.forEach(dependency -> System.out.println(dependency.toString()));
    }
}
