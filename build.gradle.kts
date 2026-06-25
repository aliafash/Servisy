tasks.register("gitInfo") {
    doLast {
        exec {
            commandLine("git", "remote", "-v")
        }
        exec {
            commandLine("git", "status")
        }
        exec {
            commandLine("git", "log", "--oneline", "-n", "10")
        }
    }
}
