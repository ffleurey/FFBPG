set title '1_UniformUniformSL_robusness: Robustness index for 5 runs (avg = 50.0)'
set xlabel 'Run'
set ylabel 'Robusness index (%)'
set xrange [0:4]
set yrange [-100:100]
plot "1_UniformUniformSL_robusness_robustness.dat" using 1 notitle with line

