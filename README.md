# Rank your colleagues

This project is used to analyze the results of the _Rank Your Colleagues_ game. The game's answers are collected using Google Forms - each round results in one .tsv file. An example file is included in [`src/test/results/`](src/test/results/).


## Usage
- Clone this repo
- Download all .tsv files, and place them in `results`
- Execute `sbt run`


## Tests
To run the tests, invoke:
```shell
sbt test
```

## Requirements
- [sbt](https://www.scala-sbt.org/download.html)  
- A JVM  