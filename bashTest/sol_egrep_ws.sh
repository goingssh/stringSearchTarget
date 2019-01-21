#!/bin/bash

> out_sep_ws.txt
while read -r line; do
    name="$line" #| xargs
    grep -E -o '(^|[[:space:]])'$name'([[:space:]]|$)' test1/testfile.txt | wc -l | xargs >> out_sep_ws.txt
done < "test1/search_terms.txt"

cat out_sep_ws.txt

err=$(diff out_sep_ws.txt test1/result_sep_ws.txt)
[[ ! -z "$err" ]] && echo "fail" $err || echo "pass"
