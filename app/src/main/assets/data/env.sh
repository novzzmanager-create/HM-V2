#!/system/bin/sh

export HYPERS_BASE="/data/local/tmp/HYPERS"
export HYPERS_DATA="$HYPERS_BASE/data"

export PATH="$HYPERS_BASE/bin:$PATH"

if [ -d "$HYPERS_DATA/Plugins" ]; then
    export HYPERSDIR="$HYPERS_DATA/Plugins"
fi

echo "HYPERS ENV LOADED AT $(date)" >> /sdcard/ZYREX-TOOLS/data/hypers_debug.log
