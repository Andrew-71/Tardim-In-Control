# ![Peripheral model](../../assets/img/blocks/rotor.png)  Rotor Block
This peripheral, attached to TARDIM's time rotor, allows you to get info about TARDIM's flight status.

Attach name - `"tardim_rotor"`

## Methods

### `isInFlight()`

Check whether the TARDIM is in flight

**Returns**

1. `bool` Whether the TARDIM is in flight

---


### `getTimeEnteredFlight()`

Get UNIX timestamp of when the TARDIM entered flight

**Returns**

1. `number` Unix timestamp or -1 if TARDIM is landed

---

### `getCurrentLocation()`

Retrieve the TARDIM's current location

**Returns**

1. `table` The location of the tardim:

   ```
    {
        dimension = "minecraft:overworld",  -- Dimension string
        pos = {  -- Coordinates
            x = 1,
            y = 2,
            z = 3
        }    
    }
   ```

---

### `getTravelLocation()`

The same as `getCurrentLocation()` but for destination
