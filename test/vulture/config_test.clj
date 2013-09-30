(ns vulture.config-test
  (:use midje.sweet)
  (:use [vulture.config])) 

(facts "about `load-config`"
       (fact "If no file is specified, it will load the default config file"
             (load-config) => {"/var/log/nginx" [{:total ".*"} "\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}"]}))
