#!/bin/sh -xe

bindir="${BASH_SOURCE[0]%/*}"
bindir="${bindir:-/}"
test -n "${bindir%%/*}" && bindir="$PWD/$bindir"

confdir="$bindir/../etc"

git svn clone 'https://svn.code.sf.net/p/displaytag/code/' --authors-file="$confdir/svn-authors.txt" -s displaytag
cd displaytag/

git show-ref | while read object ref; do
	ignore=""
	case "$ref" in
	refs/remotes/origin/tags/*)
		newref="refs/tags/${ref#refs/remotes/origin/tags/}"
		;;
	refs/remotes/origin/*)
		newref="refs/heads/${ref#refs/remotes/origin/}"
		;;
	*)
		ignore=1
	esac

	if [ -n "$ignore" ]; then
		continue
	fi

	git update-ref "$newref" "$object"
done
