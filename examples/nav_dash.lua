local tardim = peripheral.find("digital_tardim_interface")
local screen = peripheral.find("monitor")

screen.clear()
screen.setCursorBlink(false)
screen.setTextScale(0.5)

-- 15x24
--[[
~ Current pos
    X
    Y
    Z
    Dimension
    Facing
~ Destination
    X
    Y
    Z
    Dimension
    Facing
~Fuel
    Remaining
    Required


TARDIM NAV| 11

]]

local function dim_char(dimension_str)

    if dimension_str == "minecraft:overworld" then
        return "OWR"
    elseif dimension_str == "minecraft:the_nether" then
        return "NETH"
    elseif dimension_str == "minecraft:the_end" then
        return "END"
    else 
        return "???"
    end
    
end

screen.setCursorPos(math.floor((15 - 10) / 2), 1)
screen.write("TARDIM NAV")

while true do
    screen.setCursorPos(1, 3)

    local pos = tardim.getCurrentLocation()
    screen.write("Current pos")
    screen.setCursorPos(1, 4)
    screen.write("X: " .. pos.pos.x)
    screen.setCursorPos(1, 5)
    screen.write("Y: " .. pos.pos.y)
    screen.setCursorPos(1, 6)
    screen.write("Z: " .. pos.pos.z)
    screen.setCursorPos(1, 7)
    screen.write("Dimension: " .. dim_char(pos.dimension))
    screen.setCursorPos(1, 8)
    screen.write("Facing: " .. pos.facing)

    local dest = tardim.getTravelLocation()
    screen.setCursorPos(1, 10)
    screen.write("Destination")
    screen.setCursorPos(1, 11)
    screen.write("X: " .. dest.pos.x)
    screen.setCursorPos(1, 12)
    screen.write("Y: " .. dest.pos.y)
    screen.setCursorPos(1, 13)
    screen.write("Z: " .. dest.pos.z)
    screen.setCursorPos(1, 14)
    screen.write("Dimension: " .. dim_char(dest.dimension))
    screen.setCursorPos(1, 15)
    screen.write("Facing: " .. dest.facing)

    local fuel = tardim.getFuel()
    screen.setCursorPos(1, 17)
    screen.write("Fuel")
    screen.setCursorPos(1, 18)
    screen.write("REM.: " .. fuel .. "/100")
    screen.setCursorPos(1, 19)
    local Required = tardim.calculateFuelForJourney()
    screen.write("REQ.: " .. Required)
    screen.setCursorPos(1, 20)
    screen.write("ENOUGH: " .. (fuel >= Required and "YES" or "NO"))

    local inFlight = tardim.isInFlight()
    screen.setCursorPos(1, 22)
    screen.write("IN FLIGHT: " .. (inFlight and "YES" or "NO"))
end