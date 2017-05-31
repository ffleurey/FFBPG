set title 'Simulated Failure rates - Green:Initial Red:Smart Blue:Random'
set xlabel '# Servers down'
set ylabel 'Percentage of failed requests'
plot "20170531025902/ExecutionSim.txt" using 1:3 notitle with point lc rgb 'green', \
"20170531025902/RANDOM_0/ExecutionSim.txt" using 1:3 notitle with point lc rgb 'blue', \
"20170531025902/RANDOM_1/ExecutionSim.txt" using 1:3 notitle with point lc rgb 'blue', \
"20170531025902/RANDOM_2/ExecutionSim.txt" using 1:3 notitle with point lc rgb 'blue', \
"20170531025902/RANDOM_3/ExecutionSim.txt" using 1:3 notitle with point lc rgb 'blue', \
"20170531025902/RANDOM_4/ExecutionSim.txt" using 1:3 notitle with point lc rgb 'blue', \
"20170531025902/SMART_0/ExecutionSim.txt" using 1:3 notitle with point lc rgb 'red', \
"20170531025902/SMART_1/ExecutionSim.txt" using 1:3 notitle with point lc rgb 'red', \
"20170531025902/SMART_2/ExecutionSim.txt" using 1:3 notitle with point lc rgb 'red', \
"20170531025902/SMART_3/ExecutionSim.txt" using 1:3 notitle with point lc rgb 'red', \
"20170531025902/SMART_4/ExecutionSim.txt" using 1:3 notitle with point lc rgb 'red', \
"20170531031151/ExecutionSim.txt" using 1:3 notitle with point lc rgb 'green', \
"20170531031151/RANDOM_0/ExecutionSim.txt" using 1:3 notitle with point lc rgb 'blue', \
"20170531031151/RANDOM_1/ExecutionSim.txt" using 1:3 notitle with point lc rgb 'blue', \
"20170531031151/RANDOM_2/ExecutionSim.txt" using 1:3 notitle with point lc rgb 'blue', \
"20170531031151/RANDOM_3/ExecutionSim.txt" using 1:3 notitle with point lc rgb 'blue', \
"20170531031151/RANDOM_4/ExecutionSim.txt" using 1:3 notitle with point lc rgb 'blue', \
"20170531031151/SMART_0/ExecutionSim.txt" using 1:3 notitle with point lc rgb 'red', \
"20170531031151/SMART_1/ExecutionSim.txt" using 1:3 notitle with point lc rgb 'red', \
"20170531031151/SMART_2/ExecutionSim.txt" using 1:3 notitle with point lc rgb 'red', \
"20170531031151/SMART_3/ExecutionSim.txt" using 1:3 notitle with point lc rgb 'red', \
"20170531031151/SMART_4/ExecutionSim.txt" using 1:3 notitle with point lc rgb 'red', \
"20170531031624/ExecutionSim.txt" using 1:3 notitle with point lc rgb 'green', \
"20170531031624/RANDOM_0/ExecutionSim.txt" using 1:3 notitle with point lc rgb 'blue', \
"20170531031624/RANDOM_1/ExecutionSim.txt" using 1:3 notitle with point lc rgb 'blue', \
"20170531031624/RANDOM_2/ExecutionSim.txt" using 1:3 notitle with point lc rgb 'blue', \
"20170531031624/RANDOM_3/ExecutionSim.txt" using 1:3 notitle with point lc rgb 'blue', \
"20170531031624/RANDOM_4/ExecutionSim.txt" using 1:3 notitle with point lc rgb 'blue', \
"20170531031624/SMART_0/ExecutionSim.txt" using 1:3 notitle with point lc rgb 'red', \
"20170531031624/SMART_1/ExecutionSim.txt" using 1:3 notitle with point lc rgb 'red', \
"20170531031624/SMART_2/ExecutionSim.txt" using 1:3 notitle with point lc rgb 'red', \
"20170531031624/SMART_3/ExecutionSim.txt" using 1:3 notitle with point lc rgb 'red', \
"20170531031624/SMART_4/ExecutionSim.txt" using 1:3 notitle with point lc rgb 'red', \
"20170531031948/ExecutionSim.txt" using 1:3 notitle with point lc rgb 'green', \
"20170531031948/RANDOM_0/ExecutionSim.txt" using 1:3 notitle with point lc rgb 'blue', \
"20170531031948/RANDOM_1/ExecutionSim.txt" using 1:3 notitle with point lc rgb 'blue', \
"20170531031948/RANDOM_2/ExecutionSim.txt" using 1:3 notitle with point lc rgb 'blue', \
"20170531031948/RANDOM_3/ExecutionSim.txt" using 1:3 notitle with point lc rgb 'blue', \
"20170531031948/RANDOM_4/ExecutionSim.txt" using 1:3 notitle with point lc rgb 'blue', \
"20170531031948/SMART_0/ExecutionSim.txt" using 1:3 notitle with point lc rgb 'red', \
"20170531031948/SMART_1/ExecutionSim.txt" using 1:3 notitle with point lc rgb 'red', \
"20170531031948/SMART_2/ExecutionSim.txt" using 1:3 notitle with point lc rgb 'red', \
"20170531031948/SMART_3/ExecutionSim.txt" using 1:3 notitle with point lc rgb 'red', \
"20170531031948/SMART_4/ExecutionSim.txt" using 1:3 notitle with point lc rgb 'red', \

