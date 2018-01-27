package com.company;

import com.company.model.Asset;
import com.company.model.Dependency;
import com.company.service.DependencyManagement;
import com.company.service.impl.DependencyManagementImpl;

import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Main {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        DependencyManagement dependencyManagement = DependencyManagementImpl.getInstance();

        System.out.println("Enter a Command [AddDep], [CanDelete], [exit]: ");
        Scanner stdin = new Scanner(System.in);
        while (stdin.hasNext()) {
            String command = stdin.next();
            if(command.equalsIgnoreCase("AddDep")) {
                addDependency(stdin, dependencyManagement);
            } else if (command.equalsIgnoreCase("CanDelete")) {
                canDelete(stdin, dependencyManagement);
            } else if (command.equalsIgnoreCase("exit")) {
                System.exit(1);
            } else {
                System.out.println("Unrecognized command");
            }
            System.out.println("");
            System.out.println("");
            System.out.println("Enter a Command [AddDep], [CanDelete], [exit]: ");
        }
    }

    private static void addDependency(Scanner stdin, DependencyManagement dependencyManagement) throws
            InterruptedException, ExecutionException {
        System.out.println("add assetFrom: ");
        Asset assetFrom = new Asset(stdin.next());
        System.out.println("add assetTo: ");
        Asset assetTo = new Asset(stdin.next());
        Dependency dependency = new Dependency(assetFrom, assetTo);
        Future<Boolean> addFuture = dependencyManagement.insertDependency(dependency);
        while(!addFuture.isDone()) {
            System.out.println("Adding...");
            Thread.sleep(100);
        }
        if (addFuture.get()) {
            System.out.println("Added dependency: " + dependency.toString());
            System.out.println("Current dependencies: ");
            dependencyManagement.printDependencies();
        } else {
            System.out.println("Cannot add dependency: " + dependency.toString());
            System.out.println("Current dependencies: ");
            dependencyManagement.printDependencies();
        }
    }

    private static void canDelete(Scanner stdin, DependencyManagement dependencyManagement)
            throws InterruptedException, ExecutionException {
        System.out.println("Enter assetToDelete: ");
        Asset assetToDelete = new Asset(stdin.next());
        Future<Boolean> canDelete = dependencyManagement.mayDelete(assetToDelete);
        while(!canDelete.isDone()) {
            System.out.println("Checking...");
            Thread.sleep(100);
        }
        if (canDelete.get()) {
            System.out.println(assetToDelete.getId() + " Can be deleted");
        } else {
            System.out.println(assetToDelete.getId() + " Cannot be deleted");
        }
    }
}
