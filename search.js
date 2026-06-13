const fs = require('fs');

const p = '/tmp/decompiled/sources/com/maw/MainViewModel.java';
if (fs.existsSync(p)) {
    const lines = fs.readFileSync(p, 'utf8').split('\n');
    console.log("=== line 11 ===");
    console.log(lines[10]); // index 10 is line 11
} else {
    console.log("Missing MainViewModel");
}
