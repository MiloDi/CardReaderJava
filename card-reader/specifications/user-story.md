Specifications
==============

Context
-------
Our customers often want to interpret what is happening within their
industrial processes that run 24x7x365.  Various process measurements
are reported by sensors from diverse equipment.

The format of the raw sensor data is sometimes not within the direct
control of the customer, and often must be normalized or transformed
into a more useful business domain model.  Interpretation of process
data often involves computing derived concepts as defined by the
customer's subject matter experts.  Customers often desire improvement
suggestions based on the current state of a batch of data or a rolling
time-series window of data.

This exercise includes similar requirements.  We will use a deck of
cards in a random order to represent a process batch.

Each batch is represented as a json array consisting of 52 *entries*.
Each such entry is a string where the initial characters are one of
```{"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"}```
and the final character is one of the characters in ```"CHSD"```.

Similar to a shuffled deck of cards, a *valid batch* consists of each of
the possible 52 entries in a specified order.

For example, [../sample-data/batch-00.json](../sample-data/batch-00.json) represents a valid batch.

Each entry has a *rank*, a *suit*, and a *color*:

- The rank of each entry is determined by its initial character(s), where:
  the rank of "A" equals 1; the rank of "2" through "9" equals the
  corresponding integer; and the rank of "10", "J", "Q", and "K" equals 10.

- The suit of each entry is determined by its final character, where:
  the suit of "C" = Club; the suit of "H" = Heart; the suit of "S" =
  Spade; and the suit of "D" = Diamond.

- The color of each entry is determined by its suit, where: the color
  of Clubs and Spades is Black; and the color of Hearts and Diamonds
  is Red.

Customer subject matter experts have indicated that processing each
batch entails a unitless *waste* metric.  They suggest that waste for
a batch can be estimated as the sum of the *distances* between
successive entries within the batch, that is: between first and second
entries, second and third entries, third and fourth entries, etc.  The
distance between two successive entries is the absolute value of:

1. the difference of the ranks of the two entires, when the entries
   share the same suit; otherwise
2. twice the difference of the ranks of the two entries, when the
   entries share the same color; otherwise
3. three times the difference of the ranks of the two entries, when
   the entries have different colors.


Development Constraints
-----------------------
1. Use whatever language you'd like that can build / deploy to a Linux
   environment.  It should be able to be invoked from the linux
   command line as specified in the user story, after we follow the
   instructions that you provide in your deliverable's README.


User Story
----------

As the customer I would like to be able to provide a JSON
representation of a batch and:

1. receive an alarm if the batch is invalid

2. understand the waste metric for the batch

3. receive a proactive recommendation that maximizes reduction in the
   waste metric if one swap of any two entries anywhere in the batch
   could occur

4. receive a proactive recommendation that maximizes reduction in the
   waste metric if two swaps of any two entries anywhere in the batch
   could occur


I would like proactive recommendations to be presented as strings like:
```
By swapping 2H and 4C, you could reduce waste metric from 183 to 170.
```
and
```
By swapping AC and 4D, then swapping KS and 8H, you could reduce waste
metric from 203 to 165.
```

Sample input JSON documents are provided in
```sample-data/batch-*.json``` Samples 00-13 represent valid batches.
Samples 14-19 are examples of invalid batches.

As an integrator I would like to be able to invoke these capabilities
via the command line and get output on STDOUT:
```
is-invalid-batch JSON-FILE
```
```
waste-metric JSON-FILE
```
```
one-swap-recommendation JSON-FILE
```
```
two-swap-recommendation JSON-FILE
```

Acceptance Criteria
-------------------

1. Solution can be built and run using instructions provided in the
   ```README.md``` included with your deliverable.

2. Output consistent with the user story is produced for each of the
   sample data json documents.

3. Focus on correct functionality prior to performance optimizaiton.

4. Include tests for useful invariants that you can think of, as time
   permits.
