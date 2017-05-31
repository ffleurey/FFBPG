set title 'Simulated Failure rates - Green:Initial Red:Smart Blue:Random'
set xlabel '# Servers down'
set ylabel 'Percentage of failed requests'
plot "20170531025605/ExecutionSim.txt" using 1:3 notitle with point lc rgb 'green', \
"20170531025605/RANDOM_0/ExecutionSim.txt" using 1:3 notitle with point lc rgb 'blue', \
"20170531025605/RANDOM_1/ExecutionSim.txt" using 1:3 notitle with point lc rgb 'blue', \
"20170531025605/RANDOM_2/ExecutionSim.txt" using 1:3 notitle with point lc rgb 'blue', \
"20170531025605/RANDOM_3/ExecutionSim.txt" using 1:3 notitle with point lc rgb 'blue', \
"20170531025605/RANDOM_4/ExecutionSim.txt" using 1:3 notitle with point lc rgb 'blue', \
"20170531025605/SMART_0/ExecutionSim.txt" using 1:3 notitle with point lc rgb 'red', \
"20170531025605/SMART_1/ExecutionSim.txt" using 1:3 notitle with point lc rgb 'red', \
"20170531025605/SMART_2/ExecutionSim.txt" using 1:3 notitle with point lc rgb 'red', \
"20170531025605/SMART_3/ExecutionSim.txt" using 1:3 notitle with point lc rgb 'red', \
"20170531025605/SMART_4/ExecutionSim.txt" using 1:3 notitle with point lc rgb 'red', \
"20170531032711/ExecutionSim.txt" using 1:3 notitle with point lc rgb 'green', \
"20170531032711/RANDOM_0/ExecutionSim.txt" using 1:3 notitle with point lc rgb 'blue', \
"20170531032711/RANDOM_1/ExecutionSim.txt" using 1:3 notitle with point lc rgb 'blue', \
"20170531032711/RANDOM_2/ExecutionSim.txt" using 1:3 notitle with point lc rgb 'blue', \
"20170531032711/RANDOM_3/ExecutionSim.txt" using 1:3 notitle with point lc rgb 'blue', \
"20170531032711/RANDOM_4/ExecutionSim.txt" using 1:3 notitle with point lc rgb 'blue', \
"20170531032711/SMART_0/ExecutionSim.txt" using 1:3 notitle with point lc rgb 'red', \
"20170531032711/SMART_1/ExecutionSim.txt" using 1:3 notitle with point lc rgb 'red', \
"20170531032711/SMART_2/ExecutionSim.txt" using 1:3 notitle with point lc rgb 'red', \
"20170531032711/SMART_3/ExecutionSim.txt" using 1:3 notitle with point lc rgb 'red', \
"20170531032711/SMART_4/ExecutionSim.txt" using 1:3 notitle with point lc rgb 'red', \
"20170531033106/ExecutionSim.txt" using 1:3 notitle with point lc rgb 'green', \
"20170531033106/RANDOM_0/ExecutionSim.txt" using 1:3 notitle with point lc rgb 'blue', \
"20170531033106/RANDOM_1/ExecutionSim.txt" using 1:3 notitle with point lc rgb 'blue', \
"20170531033106/RANDOM_2/ExecutionSim.txt" using 1:3 notitle with point lc rgb 'blue', \
"20170531033106/RANDOM_3/ExecutionSim.txt" using 1:3 notitle with point lc rgb 'blue', \
"20170531033106/RANDOM_4/ExecutionSim.txt" using 1:3 notitle with point lc rgb 'blue', \
"20170531033106/SMART_0/ExecutionSim.txt" using 1:3 notitle with point lc rgb 'red', \
"20170531033106/SMART_1/ExecutionSim.txt" using 1:3 notitle with point lc rgb 'red', \
"20170531033106/SMART_2/ExecutionSim.txt" using 1:3 notitle with point lc rgb 'red', \
"20170531033106/SMART_3/ExecutionSim.txt" using 1:3 notitle with point lc rgb 'red', \
"20170531033106/SMART_4/ExecutionSim.txt" using 1:3 notitle with point lc rgb 'red', \
"20170531033428/ExecutionSim.txt" using 1:3 notitle with point lc rgb 'green', \
"20170531033428/RANDOM_0/ExecutionSim.txt" using 1:3 notitle with point lc rgb 'blue', \
"20170531033428/RANDOM_1/ExecutionSim.txt" using 1:3 notitle with point lc rgb 'blue', \
"20170531033428/RANDOM_2/ExecutionSim.txt" using 1:3 notitle with point lc rgb 'blue', \
"20170531033428/RANDOM_3/ExecutionSim.txt" using 1:3 notitle with point lc rgb 'blue', \
"20170531033428/RANDOM_4/ExecutionSim.txt" using 1:3 notitle with point lc rgb 'blue', \
"20170531033428/SMART_0/ExecutionSim.txt" using 1:3 notitle with point lc rgb 'red', \
"20170531033428/SMART_1/ExecutionSim.txt" using 1:3 notitle with point lc rgb 'red', \
"20170531033428/SMART_2/ExecutionSim.txt" using 1:3 notitle with point lc rgb 'red', \
"20170531033428/SMART_3/ExecutionSim.txt" using 1:3 notitle with point lc rgb 'red', \
"20170531033428/SMART_4/ExecutionSim.txt" using 1:3 notitle with point lc rgb 'red', \

