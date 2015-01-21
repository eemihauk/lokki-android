import functional_tests
import functional_tests.initialization_tests as init_tests
import unittest

from state_machine_crawler import State, Transition


class WelcomeScreenState(State):

    def verify(self):
        return functional_tests.d(text='Welcome to Lokki', packageName='com.fsecure.lokki').wait.exists(timeout=10000)

    class DoNothingTransition(Transition):
        source_state = init_tests.LokkiLaunchedState

        def move(self):
            pass


class SimpleTest(unittest.TestCase):

    def test_welcome_screen_is_shown(self):
        functional_tests.crawler.move(WelcomeScreenState)
        self.assertIs(functional_tests.crawler.state, WelcomeScreenState)
