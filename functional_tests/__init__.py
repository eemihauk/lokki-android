import initialization_tests
import welcome_screen_tests

from state_machine_crawler import State, Transition, StateMachineCrawler
from uiautomator import device as d

crawler = StateMachineCrawler(d, initialization_tests.InitialTransition)
