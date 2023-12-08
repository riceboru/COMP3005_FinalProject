# COMP3005_FinalProject
*Most of the Java code is based on Assignment 4*

## Main program:
FitnessClubDBOP.java

## IDE:
IntelliJ IDEA

## Requirement:
1. you need a database called Project_FitnessClubDB
If you have your own database, you can change it to your database name on line 21 in FitnessClubDBOP.java.
Example: private final String url = "jdbc:postgresql://localhost:5432/YOUR_DATABASE_NAME";

2. run SQL.sql in pgAdmin4. This will insert all the necessary tables and initial data for you。

## Introduction to table design:
- All ten tables are in 2NF or 3NF.
- They all have a single primary key such as "CID" or "SID". Other data elements correspond to the primary keyword one-to-one
- Non-primary key columns such as Name, Available, FitnessGoals, HealthMetrics, Time, etc. completely depend on the primary key of their respective tables.
- There will be some one-to-many relationships or many-to-many relationships in the project, such as member and group class. Members can join different group classes, and group classes can accommodate many students, resulting in data with many duplicate groups. So I divided it into additional tables such as EquipmentMaintain, PersonalTrain, MemberJoinGroupClass, etc.

## Introduction to video and program features:
  I divided the video into several parts, each video corresponds to a main function:
  
  ###1. Manage all users
    **Related videos**: Video1_manage_users.mp4
    **Description**: 
    Staff interface: allows users to browse and add new staff, coaches and member information. You can also modify some basic information such as members' Fitness goals and Health metrics.
    The video shows how to browse all members and add users. Due to the length of the video, it only shows the information about adding and modifying members, because the usage of staff and coaches is similar to that of members.

  ###2. Manage group class
    **Related videos**: Video2_Manage_groupclass.mp4
    **Description**: 
