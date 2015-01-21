import functional_tests
import functional_tests.crawler as crawler
import unittest

from state_machine_crawler import State, Transition


class WelcomeScreenState(State):

    def verify(self):
        return functional_tests.d(text='Welcome to Lokki', packageName='com.fsecure.lokki').wait.exists(timeout=10000)

    class DoNothingTransition(Transition):
        source_state = crawler.LokkiLaunchedState

        def move(self):
            pass


class SimpleTest(unittest.TestCase):

    def test_lokki_is_installed(self):
        functional_tests.cr.move(crawler.InstalledState)
        self.assertIs(functional_tests.cr.state, crawler.InstalledState)

    def test_lokki_is_launched(self):
        functional_tests.cr.move(crawler.LokkiLaunchedState)
        self.assertIs(functional_tests.cr.state, crawler.LokkiLaunchedState)

    def test_welcome_screen_is_shown(self):
        functional_tests.cr.move(WelcomeScreenState)
        self.assertIs(functional_tests.cr.state, WelcomeScreenState)
