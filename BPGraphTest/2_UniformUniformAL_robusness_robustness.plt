set title '2_UniformUniformAL_robusness: Robustness index for 5 runs (avg = 85.1010752688172)'
set xlabel 'Run'
set ylabel 'Robusness index (%)'
set xrange [0:4]
set yrange [-100:100]
plot "2_UniformUniformAL_robusness_robustness.dat" using 1 notitle with line

