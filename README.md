<p align="center"> 
  <img src="https://github.com/user-attachments/assets/9316401c-4f8a-4b70-bd04-9da1bcbccc1f"alt="FerrumL logo">
</p>

[![Project Stats](https://openhub.net/p/FerrumL/widgets/project_thin_badge.gif)](https://openhub.net/p/FerrumL)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=Egg-03_FerrumL&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=Egg-03_FerrumL)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=Egg-03_FerrumL&metric=bugs)](https://sonarcloud.io/summary/new_code?id=Egg-03_FerrumL)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=Egg-03_FerrumL&metric=coverage)](https://sonarcloud.io/summary/new_code?id=Egg-03_FerrumL)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=Egg-03_FerrumL&metric=duplicated_lines_density)](https://sonarcloud.io/summary/new_code?id=Egg-03_FerrumL)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=Egg-03_FerrumL&metric=security_rating)](https://sonarcloud.io/summary/new_code?id=Egg-03_FerrumL)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=Egg-03_FerrumL&metric=reliability_rating)](https://sonarcloud.io/summary/new_code?id=Egg-03_FerrumL)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=Egg-03_FerrumL&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=Egg-03_FerrumL)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=Egg-03_FerrumL&metric=vulnerabilities)](https://sonarcloud.io/summary/new_code?id=Egg-03_FerrumL)

[![License](https://img.shields.io/github/license/Egg-03/FerrumL)](https://github.com/Egg-03/FerrumL/blob/main/LICENSE)
![Repo Size](https://img.shields.io/github/repo-size/Egg-03/FerrumL)
![Language](https://img.shields.io/github/languages/top/Egg-03/FerrumL)
![Release Version](https://img.shields.io/github/v/release/Egg-03/FerrumL)
![Release Date](https://img.shields.io/github/release-date/Egg-03/FerrumL)
![Downloads](https://img.shields.io/github/downloads/Egg-03/FerrumL/total)
![Commits to main since latest release](https://img.shields.io/github/commits-since/Egg-03/FerrumL/latest)

# About
FerrumL (for Windows Vista and 7) is a lightweight Hardware and Network Information wrapper written in pure Java. It contacts some [Computer System Hardware Classes](https://learn.microsoft.com/en-us/windows/win32/cimwin32prov/computer-system-hardware-classes) and [Operating System Classes](https://learn.microsoft.com/en-us/windows/win32/cimwin32prov/operating-system-classes) of Windows Management Instrumentation (WMI) through [WMIC](https://learn.microsoft.com/en-us/windows/win32/wmisdk/wmic) to fetch comprehensive system details on Windows Operating Systems.

# Supported Operating Systems
FerrumL has been designed to support legacy OSes like Windows 7 and Vista but also supports Windows 8, 8.1, 10 and 11 (as long as Microsoft does not pull the plug from WMIC)
FerrumL also lacks some features FerrumX offers and is more error-prone than FerrumX because some sections of hardware in FerrumL are sorted by their names and not their Device IDs.
I will detail them later. Unless you absolutely need FerrumL, I would recommend [FerrumX](https://github.com/Egg-03/FerrumX) instead

# Installation
<h4>For Developers:</h4>
If you wish to use the wrapper in your own project, you can do so by downloading the main jar file along with it's documentation

- Download the main jar file *[FerrumL-core-vX.X.X]* from [FerrumL Releases](https://github.com/Egg-03/FerrumL/releases)
- [Optional] Download the javadoc jar file *[FerrumL-doc-vX.X.X]*
- Import the main jar file as a classpath dependency in your project.
- [Optional] Attach the javadoc to the jar file

The methods for linking an external jar file with its javadoc as a dependency varies with the IDE you will use. You should be able to find online help for it and if I get time, I'll 
attach the tutorials for Eclipse and IntelliJ Idea

# Dependencies
> The wrapper relies on optional dependencies, the testing framework JUnit5 and the logging framework TinyLog 2.7.0 for testing code

# Documentation
Documentation can be found [here](https://egg-03.github.io/FerrumL-Documentation/)

# Usage
> Wiki under construction

You can also check out some examples [here](https://github.com/Egg-03/FerrumL/tree/96dc8a18b3724b72589ade0c372386503ec4c4f5/src/com/ferruml/tests)
# License
This project is licensed under the MIT License. Read the LICENSE.md for more information.

# Special Thanks
[Rugino3](https://github.com/Soumil-Biswas) for the banner and documentation proofreading
