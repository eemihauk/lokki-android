import unittest
import functional_tests
import functional_tests.utils as utils

from state_machine_crawler import State, Transition


class InstalledState(State):

    def verify(self):
        cmd = "adb shell 'pm list packages -f | grep com.fsecure.lokki'"
        return utils.call(cmd)


class InitialTransition(Transition):
    target_state = InstalledState

    def move(self):
        cmd1 = "adb shell pm clear com.fsecure.lokki"
        utils.call(cmd1)
        cmd2 = "adb install -r App/build/outputs/apk/lokki-*-debug.apk"
        utils.call(cmd2)


class LokkiLaunchedState(State):

    def verify(self):
        return functional_tests.d(packageName='com.fsecure.lokki').wait.exists(timeout=10000)

    class LaunchLokkiTransition(Transition):
        source_state = InstalledState

        def move(self):
            cmd = "adb shell am start -n com.fsecure.lokki/com.fsecure.lokki.MainActivity"
            utils.call(cmd)


class InitializationTest(unittest.TestCase):

    def test_lokki_is_installed(self):
        functional_tests.crawler.move(InstalledState)
        self.assertIs(functional_tests.crawler.state, InstalledState)

    def test_lokki_is_launched(self):
        functional_tests.crawler.move(LokkiLaunchedState)
        self.assertIs(functional_tests.crawler.state, LokkiLaunchedState)
