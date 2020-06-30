
CS 585 - Project 2
Team - Elene Kavtaradze and Michael Ludwig
author: Elene Kavtaradze - Problems 1 & 2 - dataset P was shared with Michael for Problem 4
Michael Ludwig - Problems 3 & 4

How to run 
(to run with included Intellij project)

Problem 1
    Part 1 - creatingDatasets
        Takes a while to generate two 100 mb files. 
        Run as application
        No Program Inputs
    Part 2 - spatialJoin
        Run as application
        Program Inputs: datasetP.txt datasetR.txt spatialJoinOutPut
        Output: Rectangle(double,double,double,double) Point(double,double)
        ex: smallTestingData/datasetP.txt smallTestingData/datasetR.txt spatialJoinOutput
            smallerTestingData/datasetP.txt smallerTestingData/datasetR.txt spatialJoinOutput
            smallTestingData/datasetP.txt smallTestingData/datasetR.txt spatialJoinOutput W(1,3,3,20)
        
        
Problem 2 - kMeans
    Run as application
    Program inputs: datasetP.txt centroids.txt kMeansOutput
    Output: Centroids(double,double)
    
        ex: smallTestingData/datasetP.txt smallTestingData/datasetR.txt kMeansOutput
            smallerTestingData/datasetP.txt smallerTestingData/datasetR.txt kMeansOutput
   

Information/Assumptions
Dataset P - Points
    Pairs are split by "," 
    example: 12,34
    Points generated are ints, but mappers can handle doubles
Centroids
    Pairs are split by " "
    example: 12 34
    Points generated are ints, but mappers can handle doubles
Dataset R - Points + height + width
    Split by "," 
    example: 1436,5296,9,4
    X direction is Width
    Y direction is height
    