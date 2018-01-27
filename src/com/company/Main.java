package com.company;

import com.company.model.Asset;
import com.company.model.Dependency;
import com.company.service.DependencyManagement;
import com.company.service.impl.DependencyManagementImpl;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
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

    private static void addDependency(Scanner stdin, DependencyManagement dependencyManagement) {
        System.out.println("add assetFrom: ");
        Asset assetFrom = new Asset(stdin.next());
        System.out.println("add assetTo: ");
        Asset assetTo = new Asset(stdin.next());
        Dependency dependency = new Dependency(assetFrom, assetTo);
        if (dependencyManagement.insertDependency(dependency)) {
            System.out.println("Added dependency: " + dependency.toString());
            System.out.println("Current dependencies: ");
            dependencyManagement.printDependencies();
        } else {
            System.out.println("Cannot add dependency: " + dependency.toString());
            System.out.println("Current dependencies: ");
            dependencyManagement.printDependencies();
        }
    }

    private static void canDelete(Scanner stdin, DependencyManagement dependencyManagement) {
        System.out.println("Enter assetToDelete: ");
        Asset assetToDelete = new Asset(stdin.next());
        if (dependencyManagement.mayDelete(assetToDelete)) {
            System.out.println(assetToDelete.getId() + " Can be deleted");
        } else {
            System.out.println(assetToDelete.getId() + " Cannot be deleted");
        }
    }
}
