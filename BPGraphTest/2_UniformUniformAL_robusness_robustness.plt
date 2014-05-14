set title '2_UniformUniformAL_robusness: Robustness index for 25 runs (avg = 89.27000701754386)'
set xlabel 'Run'
set ylabel 'Robusness index (%)'
set xrange [0:24]
set yrange [-100:100]
plot "2_UniformUniformAL_robusness_robustness.dat" using 1 notitle with line

