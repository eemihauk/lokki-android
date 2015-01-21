import sys
import unittest
import functional_tests

from subprocess import Popen, PIPE, STDOUT
from state_machine_crawler import State, Transition


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


class InstalledState(State):

    def verify(self):
        cmd = "adb shell 'pm list packages -f | grep com.fsecure.lokki'"
        return call(cmd)


class InitialTransition(Transition):
    target_state = InstalledState

    def move(self):
        cmd1 = "adb shell pm clear com.fsecure.lokki"
        call(cmd1)
        cmd2 = "adb install -r App/build/outputs/apk/lokki-*-debug.apk"
        call(cmd2)


class LokkiLaunchedState(State):

    def verify(self):
        return functional_tests.d(packageName='com.fsecure.lokki').wait.exists(timeout=10000)

    class LaunchLokkiTransition(Transition):
        source_state = InstalledState

        def move(self):
            cmd = "adb shell am start -n com.fsecure.lokki/com.fsecure.lokki.MainActivity"
            print "ABOUT TO START ACTIVITY"
            call(cmd)

