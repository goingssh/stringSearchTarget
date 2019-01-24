#!/bin/bash

testdir=$1
if [ ! -z "$testdir" ]
then
    echo "counts:" 
else
    echo
    echo "must include test directory! usage: ./sol_grep_all.sh testdir"
    echo
    exit 1
fi

outfile="out_substr.txt"
> $outfile
while read -r line; do
    name="$line"
    grep -o $name $testdir"/testfile.txt" | wc -l | xargs >> $outfile
done < $testdir"/search_terms.txt"

cat $outfile

err=$(diff $outfile $testdir"/result_substr.txt")
[[ ! -z "$err" ]] && echo "fail" $err || echo "pass"
