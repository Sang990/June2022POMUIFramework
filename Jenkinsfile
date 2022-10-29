pipeline {
    
    agent any
    
    stages {
        
        stage("Build"){
            steps{
                echo("Build project") 
            }
        }
        
        stage("Deploy to Dev"){
            steps{
                echo("deploy to dev env")
            }
        }
        
        stage("Run UTs"){
            steps{
                echo("Run Unit Test cases")
            }
        }
        
        stage("Deploy to QA"){
            steps{
                echo("deploy to QA env")
            }
        }
        
        stage("Run Regression Automation Test Cases"){
            steps{
                echo("run regression tests")
            }
        }
        
        stage("Deploy to Stage"){
            steps{
                echo("deploy to stg env")
            }
        }
        
        stage("Run Automation Sanity Test cases"){
           steps{
                echo("run sanity tests")
           }
        }
        
        stage("Deploy to Prod"){
           steps{
                echo("deploy to prod env")
           }
        }
    }
    
    
}