#!/bin/bash
. ./setEnv.sh

# stop servers and locators
gfsh stop server --dir=server1
gfsh stop server --dir=server2
gfsh stop locator --dir=locator1
