(ns analysis-test
  (:require
    [clojure.test :refer [deftest testing is]]
    [finance.analysis :refer :all]
    [clojure.data :refer [diff]]))

(deftest split-columns-test
  (testing "should split a string with the separator semicolon"
    (is (= [["das" "ist" "ein Test"]] (split-columns ["das;ist;ein Test"]))))

  (testing "should ignore empty strings"
    (is (= '() (split-columns [])))))

;(deftest sieve-data-test
;  (testing "should find data rows with nine columns"
;    (is (= 1 (count (sieve-data [(take 9 (range))
;                                 (take 8 (range))]))))))

;(deftest grouping-test
;  (testing "should group lines by value in third column"
;    (let [input [[0 1 2 3]
;                 [0 1 2 3]
;                 [1 2 3 4]]
;          expected {2 [[0 1 2 3]
;                       [0 1 2 3]]
;                    3 [[1 2 3 4]]}
;          ]
;      (is (= expected (grouping input))))))
;
;(deftest fix-number-format-test
;  (testing "should format number string to machine readable format"
;    (is (= "24.23" (fix-number-format "24,23")))
;    (is (= "13911.43" (fix-number-format "13.911,43")))))
;
;(deftest read-settings-test
;  (testing "should read settings from file into map"
;    (is (not-empty (get-in (read-settings "settings.json") [:categories :einkaufen])))))
;
;(deftest to-number-test
;  (testing "should parse a valid string to a floating point number"
;    (is (= (float 25.91) (to-number ["25,91" ""]))))
;  (testing "should return the absolute value of a floating point number"
;    (is (= (float 67.21) (to-number ["-67,21" ""])))))
;
;(deftest has-numbers-only?-test
;  (testing "should evaluate if the second last values in a collection are numbers"
;    (is (has-numbers-only? [["34,91" ""]
;                            ["-20,49" ""]]))
;    (is (not (has-numbers-only? [["40,12" ""]
;                                 ["keine Nummer" ""]])))
;    (is (not (has-numbers-only? [["-43,op" ""]
;                                 ["56,01" ""]])))))
;
;(deftest group-by-categories-test
;  (testing "should group data by specified categories"
;    (let [data [["20.08.2021"
;                 "20.08.2021"
;                 "Vodafone Deutschland GmbH"
;                 "Lastschrift"
;                 "08/2021 K-NR. 332855551 Ihre Rechnu ng online bei www.vodafone.de/meink abel"
;                 "1.762,71"
;                 "EUR"
;                 "-19,99"
;                 "EUR"]
;                ["18.08.2021"
;                 "18.08.2021"
;                 "PAYONE GmbH"
;                 "Lastschrift"
;                 "BIO COMPANY SE//Glienicke Nordbahn/ DE 2021-08-17T09:59:04 Folgenr.000 Verfalld.2022-12"
;                 "1.783,69"
;                 "EUR"
;                 "-96,22"
;                 "EUR"]
;                ["19.08.2021"
;                 "19.08.2021"
;                 "PayPal (Europe) S.a.r.l. et Cie., S .C.A."
;                 "Lastschrift"
;                 "PP.4503.PP . ITUNESAPPST, Ihr Einka uf bei ITUNESAPPST"
;                 "1.782,70"
;                 "EUR"
;                 "-0,99"
;                 "EUR"]]
;          categories {
;                      :einkaufen ["BIO COMPANY" "Neuland Fleischer"]
;                      :internet  ["1&1" "Vodafone"]
;                      }
;          expected
;
;          {:einkaufen  [96.22 [["18.08.2021"
;                                "18.08.2021"
;                                "PAYONE GmbH"
;                                "Lastschrift"
;                                "BIO COMPANY SE//Glienicke Nordbahn/ DE 2021-08-17T09:59:04 Folgenr.000 Verfalld.2022-12"
;                                "1.783,69"
;                                "EUR"
;                                "-96,22"
;                                "EUR"]]]
;           :internet   [19.99 [["20.08.2021"
;                                "20.08.2021"
;                                "Vodafone Deutschland GmbH"
;                                "Lastschrift"
;                                "08/2021 K-NR. 332855551 Ihre Rechnu ng online bei www.vodafone.de/meink abel"
;                                "1.762,71"
;                                "EUR"
;                                "-19,99"
;                                "EUR"]]]
;           :not-mapped [0.99 [["19.08.2021"
;                               "19.08.2021"
;                               "PayPal (Europe) S.a.r.l. et Cie., S .C.A."
;                               "Lastschrift"
;                               "PP.4503.PP . ITUNESAPPST, Ihr Einka uf bei ITUNESAPPST"
;                               "1.782,70"
;                               "EUR"
;                               "-0,99"
;                               "EUR"]]]
;           }
;          actual (group-by-categories data categories)]
;      (is (= '(nil nil expected) (diff expected actual)))
;      (is (= expected actual))
;      )))
;
;(is (= {:a 1 :b [[1] 2]} {:a 1 :b [[1] 2]}))