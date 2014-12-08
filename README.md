Test-Agent
==========

### How to use? ###

#### Open Agent ####

    adb shell am start -n edu.ntut.csie.sslab1321.testagent/edu.ntut.csie.sslab1321.testagent.DummyActivity

#### Close Agent####

    adb shell am broadcast -a testagent -e action STOP_TESTAGENT

#### Add Contact ####

 

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
