#!/bin/bash

> out_all.txt
while read -r line; do
    name="$line" #| xargs
    #echo $name
    grep -o $name test1/testfile.txt | wc -l | xargs >> out_all.txt
done < "test1/search_terms.txt"

cat out_all.txt

err=$(diff out_all.txt test1/result_all_substr.txt)
[[ ! -z "$err" ]] && echo "fail" $err || echo "pass"
