# Dynamic-Memory-Allocator

A dynamic memory allocator implemented in Java, as part of a COL106 Data Structures and Algorithms Assignment for the fall semester 2020.

This memory allocator has 3 different implementations for memory allocation and freeing using Linked Lists, Binary Search Trees and balanced BSTs(AVL Trees). It allots memory using the first fit and best fit algorithms.

It maintains two data structures of any of the aforementioned implementations above to denote memory that has already been allocated and memory that is free.

The data structure containing allocated memory has the starting address of the memory as the ```key```, whereas the data structure with the free memory contains the size of the memory block as the ```key```, and this program takes in different commands to allocate and free the memory.

After cloning and extracting and compiling the whole code, run the ```run.sh``` to input the commands. The format of the commands is given below:

### Format of the 3 types commands
```Allocate memory_size```

```Free starting_Address```

```Defragment 0```

### A general example of the command

``` 
Number of test cases (format of 1 test case in general is given below)

Size of total memory allocated for this test case

Total number of commands to be executed

1st command

2nd command
.
.
.
nth command (this TC ends here)
```
