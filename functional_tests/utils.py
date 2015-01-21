import sys

from subprocess import Popen, PIPE, STDOUT


class CommandExecutionError(Exception):
    pass


def call(*command):
    process = Popen(command, shell=True, stdout=PIPE, stderr=STDOUT)

    outputs = []

    # Poll process for new output until finished
    while True:
        nextline = process.stdout.readline()
        if nextline == '' and process.poll() is not None:
            break
        sys.stdout.write(nextline)
        outputs.append(nextline)
        sys.stdout.flush()

    output = "\n".join(outputs)
    exitCode = process.returncode

    if exitCode == 0:
        return output
    else:
        raise CommandExecutionError(command, exitCode, output)
