# Rank your colleages

This project analyzes the results of the _Rank Your Colleagues_ game. For each round of the game, there is a .tsv file (export from Google Forms). One example file is included in [`src/test/results/example.tsv`](src/test/results/example.tsv).


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