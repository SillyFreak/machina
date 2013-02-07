machina
=======

an experimental stack-based machine

Machina is not meant to support everything expected from a production-quality
virtual machine. It is an experimental machine trying to do interesting things
that are missing from the JVM:

- Multiple return types: returning from a function can push multiple values
  onto the caller's stack
- Pointers
- and probably others

Notable things missing for more sophisticated hacking:
- almost any functionality besides (32-bit) integer arithmetic, including
  branching
- a heap and object orientation
- better structured core VM functionality (e.g. the PC is not part of the VM's
  memory)
