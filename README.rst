==========
Annotation
==========

This source code contains integration tests for **Knowit** automation task.

============
Used toolkit
============

- ``Selenide`` (http://ru.selenide.org/) to manipulate browser.
- ``Allure`` (http://allure.qatools.ru/) to generate beautiful reports.
- ``Video Recorder`` (http://automation-remarks.com/video-recorder-java/) to capture video of launched tests.

=================
Required software
=================

Please be sure that you have installed:

- ``java1.8, maven3.3``
- ``google-chrome``
- ``ffmpeg`` (for video capture)

If you are not going to use video capture feature, just use option ``-Dvideo.disable=true`` in tests launching command.

===================
How to launch tests
===================

Please, first of all start web application in terminal::

    $ mvn jetty:run

Then, in another terminal session execute next command::

    $ mvn integration-test

======================
How to generate report
======================

Please be sure that you have installed `allure-cli <http://wiki.qatools.ru/display/AL/Allure+Commandline>`_.
Then after tests finishing execute in terminal next commands::

    $ allure generate target/allure-results -o allure-report
    $ allure report open --report-dir allure-report -p 20000

Then new browser window will be opened and navigation to local URL with report happens.
If navigation doesn't happen by default, you can open it manually http://localhost:20000/.

===============
Choice of tests
===============

To specify a separated test you can use command option ``-Dtest=ClassName#testName`` like that::

    $  mvn test -Dtest=OriginalDataTest#studentsCountCorrespondData

More information about such ability is available in
`official documention <https://maven.apache.org/surefire/maven-surefire-plugin/examples/single-test.html>`_.
