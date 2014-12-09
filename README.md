Test-Agent
==========


## How to use? ##
-----

### Open Agent ###

    adb shell am start -n edu.ntut.csie.sslab1321.testagent/edu.ntut.csie.sslab1321.testagent.DummyActivity

### Close Agent###

    adb shell am broadcast -a testagent -e action STOP_TESTAGENT

### Add Contact ###
 

    adb shell am broadcast -a testagent -e action ADD_CONTACT -e attribute value



**Attribute:**


- NAME

- PHONE

- HOME_NUMBER

- WORK_NUMBER

- HOME_EMAIL

- WORK_EMAIL

- COMPANY_NAME

- JOB_TITLE

### Open App ###


    adb shell am broadcast -a testagent -e action OPEN_APP -e name appName

### Turn on/off wifi ###

**Turn on**

    adb shell am broadcast -a testagent -e action TURN_ON_WIFI

**Turn off**
    
    adb shell am broadcast -a testagent -e action TURN_OFF_WIFI

### Turn on/off mobile data ###

**Turn on**

    adb shell am broadcast -a testagent -e action TURN_ON_MOBILE_DATA

**Turn off**
    
    adb shell am broadcast -a testagent -e action TURN_OFF_MOBILE_DATA