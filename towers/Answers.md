#  MCON 264 — Recursion Assignment: Towers of Hanoi × 3


##  Overview

In this lab, you implemented three recursive versions of the Towers of Hanoi problem and (optionally) one iterative version.  
Each part reinforces a key concept of recursion — base case, recursive case, and growth pattern — and illustrates how recursion can be refactored or replaced by iteration.

---

## Part 1 — Classic Towers of Hanoi (`TowersOfHanoi.java`)

### 1. Base Case
_Describe the base condition that stops recursion (for example, what happens when `n == 0`?)._

> When `n == 0`, the recursion stops and the method simply returns without doing anything. This represents the case where there are no disks to move, so no action is needed.

### 2. Recursive Case
_Explain the sequence of recursive calls and what each represents._

> The recursive case follows three steps:
> 1. `solve(n-1, from, to, aux, moves)` - Move the top n-1 disks from the source peg to the auxiliary peg (using the destination peg as a helper)
> 2. `moves.add(from + " -> " + to)` - Move the largest disk (disk n) directly from the source peg to the destination peg
> 3. `solve(n-1, aux, from, to, moves)` - Move the n-1 disks from the auxiliary peg to the destination peg (using the source peg as a helper)
> 
> This breaks the problem into smaller subproblems, allowing us to move all disks while maintaining the constraint that larger disks never sit on smaller ones.

### 3. Sample Trace (for n = 3)

| Move # | From | To |
|:--:|:--:|:--:|
| 1 | A | C |
| 2 | A | B |
| 3 | C | B |
| 4 | A | C |
| 5 | B | A |
| 6 | B | C |
| 7 | A | C |

_Total moves = 2ⁿ − 1 = 7 (for n = 3)_

---

## Part 2 — Counting Moves (`TowersExercise21.java`)

### 1. Approach
_How did you modify the standard recursion to count rather than print moves?_

> Instead of adding moves to a list, I used a static variable `count` that increments each time a disk is moved. The recursive structure remains the same (base case when n==0, recursive case moving n-1 disks twice with one direct move in between), but instead of recording the move details, we simply increment `count++` for each disk movement. The count is reset to 0 at the beginning of each problem run in the main method.

### 2. Verification of Formula
_Complete the table and verify that count = 2ⁿ − 1._

| n | Expected (2ⁿ − 1) | Program Output | Matches? (Y/N) |
|:--:|:--:|:--:|:--:|
| 1 | 1 | 1 | Y |
| 2 | 3 | 3 | Y |
| 3 | 7 | 7 | Y |
| 4 | 15 | 15 | Y |
| 5 | 31 | 31 | Y |

### 3. Reflection
_What changes when you replace printed moves with a counter? What are the pros and cons?_

> **Pros:** Counting is more efficient (no string creation/storage), uses less memory, and makes it easier to verify the theoretical formula. It's also faster for large n since we're not building large lists or printing output.
> 
> **Cons:** We lose the detailed information about which specific moves were made, making it harder to debug or verify correctness visually. We can't trace the exact sequence of moves or see if the algorithm is working correctly step-by-step.

---

## Part 3 — Hanoi Variation (`TowersVariations.java`)

### 1. New Rule
_Every move must pass through the middle peg. How does this alter the recursion?_

> The constraint that every move must pass through the middle peg means we can't move disks directly between pegs 1 and 3. This changes the recursion significantly:
> - Moving a single disk from peg 1 to peg 3 now requires TWO moves (1→2, then 2→3)
> - The recursive case makes THREE calls to `solveVariation(n-1)`: first moving n-1 disks from source to middle, then from middle back to source (to make room), then finally from source to destination
> - Between the recursive calls, we make 2 direct moves: from→mid and mid→to, adding 2 to the count
> - The pattern is: `solve(n-1, from, mid, to)`, print move from→mid, `solve(n-1, to, mid, from)`, print move mid→to, `count+=2`, then `solve(n-1, from, mid, to)` again
> This dramatically increases the total number of moves required.

### 2. Observed Move Counts

| n | Expected ≈ 3ⁿ − 1 | Program Output | Matches? (Y/N) |
|:--:|:--:|:--:|:--:|
| 1 | 2 | 2 | Y |
| 2 | 8 | 8 | Y |
| 3 | 26 | 26 | Y |
| 4 | 80 | 80 | Y |

### 3. Analysis
_Why does this variation grow faster than the standard version? How do additional move constraints affect complexity?_

> This variation grows faster (3ⁿ vs 2ⁿ) because the middle-peg constraint forces every disk movement to become two physical moves. The recurrence relation changes from T(n) = 2T(n-1) + 1 to T(n) = 3T(n-1) + 2:
> - We still make recursive calls for n-1 disks three times total
> - But moving the largest disk now takes 2 moves instead of 1
> - This causes the exponential base to increase from 2 to 3
> 
> Additional constraints like this increase complexity because they restrict our options and force us to take longer paths. Each added constraint typically increases the base of the exponential growth, making the problem intractable for larger n much faster.

---

## Comparative Analysis

### 1. Growth Patterns

| Version | Approx. Formula | Growth Type |
|:--|:--|:--|
| Standard | 2ⁿ − 1 | Exponential |
| Variation | 3ⁿ − 1 | Exponential (faster) |
| Iterative (Optional) | 2ⁿ − 1 | Same as recursive but loop based |

### 2. Stack Depth and Memory
_Estimate the maximum recursion depth before StackOverflowError and discuss how stack size (–Xss flag) affects this._

> The maximum recursion depth is equal to n (the number of disks) since each recursive call reduces n by 1 until reaching the base case n=0. 
> 
> With the default Java stack size (~1MB), we can typically handle recursion depths of several thousand calls before a StackOverflowError occurs. For Towers of Hanoi, this means we could solve problems with n ≈ 10,000+ disks from a stack perspective (though the 2ⁿ time complexity makes this impractical).
> 
> The `-Xss` flag allows us to increase the stack size (e.g., `-Xss2m` for 2MB), which would allow even deeper recursion. However, for Towers of Hanoi, the exponential time complexity (2ⁿ moves) becomes the bottleneck long before stack overflow is an issue—even n=64 requires over 18 quintillion moves.

---

## Part 4 — Beyond Recursion (Extra Credit)

### Iterative / Explicit-Stack Version (`TowersIterative.java`)

1. How does your iterative version simulate recursion?
2. How did you track pending calls or frames?
3. Which version (r vs iterative) is clearer? Why?

> ✎ Your answer here

---

## Discussion &amp; Reflection

1. What three questions can you use to verify a recursive solution works?
2. How do the base case and recursive case cooperate to guarantee termination?
3. What is the trade-off between elegance and efficiency in recursion?

> **Three verification questions:**
> 1. Does the base case handle the simplest input correctly and stop recursion?
> 2. Does the recursive case make progress toward the base case (reducing the problem size)?
> 3. Assuming the recursive calls work correctly for smaller inputs, does the current call produce the correct result?
> 
> **Base case and recursive case cooperation:**
> The recursive case reduces the problem size with each call (n → n-1), ensuring we eventually reach the base case (n=0). The base case stops the recursion without making further calls. Together, they guarantee termination because: (1) we always move toward the base case, and (2) the base case always terminates without further recursion.
> 
> **Elegance vs. efficiency trade-off:**
> Recursion offers elegant, concise solutions that closely mirror mathematical definitions and are often easier to understand and prove correct. However, this comes at the cost of:
> - Memory overhead from function call stack frames
> - Potential stack overflow for deep recursion
> - Possible redundant calculations in naive recursive solutions
> - Generally slower execution compared to optimized iterative approaches
> 
> For Towers of Hanoi specifically, the recursive solution is both elegant AND efficient (no redundant work), but it still uses O(n) stack space that an iterative version could avoid.

---

## References (APA or MLA format)

- Dale, N., Joyce, D., &amp; Weems, C. (2016). *Object-Oriented Data Structures Using Java* (Ch. 3 Recursion). Jones &amp; Bartlett.
- Additional videos or websites you consulted (YouTube CS50 Recursion, GeeksForGeeks Hanoi, etc.)

---

 **Submission Checklist**

- [ ] `TowersOfHanoi.java` — implemented and tested
- [ ] `TowersExercise21.java` — counts moves correctly
- [ ] `TowersVariations.java` — middle-peg constraint implemented
- [ ] (Extra Credit) `TowersIterative.java` — loop or explicit stack solution
- [ ] All JUnit tests pass (green on GitHub Actions)
- [ ] This `ANSWERS.md` file completed and committed to repo  
