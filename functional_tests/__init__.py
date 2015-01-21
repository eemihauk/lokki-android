import crawler
import base_tests
from state_machine_crawler import State, Transition, StateMachineCrawler
from uiautomator import device as d

cr = StateMachineCrawler(d, crawler.InitialTransition)
