# random-edn

Generate random EDN data with test.check and benchmark tools.reader
with criterium.

## Usage

The versions of tools.reader that are to be benchmarked must be
resolvable by leiningen. The easiest way to do so is to modify your
checked out tools.reader's version, and run `lein install`.

When the dependencies are configured:

```
lein bench
```
