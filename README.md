# Birthday-Scheduler
An automated birthday wish sending application for you friends. Can be tweaked for other email sending purposes.

The application makes using of Spring 4 , Hibernate 5 , Spring Scheduler for scheduling the job to run at the destined time and Velocity Template Engine for formatting and Spring Java Mailer for sending emails.

This application can also be used as a spam tool for filling inboxes by tweaking the cronExpression.

So guys, have fun and LET THE CODE BE WITH YOU.

Instructions:

In the HSQLDB folder the hsql.bat file with start the hsqldb server and hsqldb-gui.bat with execute the GUI client for HSQLDB.
You may have to edit the bat files to point it to your m2 repo path.

In the code, in beans.xml you may have to add in your email address and password for congifuring your email address through which you will be able to send the auto-gen emails.

Since this is a maven project you will need maven to build it.

I am also in the process of building a web client for this application and also a few more updates. So stay tuned
